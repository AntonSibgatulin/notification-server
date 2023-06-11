package jp.konosuba.notificationserver.config;


import jp.konosuba.notificationserver.data.contact.Contacts;
import jp.konosuba.notificationserver.data.contact.ContactsMapper;
import jp.konosuba.notificationserver.controllers.contacts.util.ContactModel;
import jp.konosuba.notificationserver.data.user.user.UserRepository;
import jp.konosuba.notificationserver.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.jobrunr.configuration.JobRunr;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.jobrunr.utils.mapper.jackson.JacksonJsonMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {


    private final UserRepository userRepository;

   /* @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");

            }
        };
    }
    */

    @Bean
    public UserDetailsService userDetailsService() {
        return phone ->
                userRepository.findUserByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("Phone number not found"));

    }

    @Bean
    public ContactsMapper contactsMapper(){
        return new ContactsMapper() {
            @Override
            public Contacts fromContactModeltoContacts(ContactModel contactModel) {
                Contacts contacts = new Contacts();
                contacts.setEmail(contactModel.getEmail());
                contacts.setName(contactModel.getName());
                contacts.setPhone(StringUtils.formateCellPhone(contactModel.getPhone()));
                contacts.setRelative(contactModel.getRelative());
                contacts.setTg(contactModel.getTg());
                contacts.setWs(contactModel.getWs());
                contacts.setVk(contactModel.getVk());

                return contacts;
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
