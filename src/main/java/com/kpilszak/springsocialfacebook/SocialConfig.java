package com.kpilszak.springsocialfacebook;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Override
    public void addConnectionFactories(
            ConnectionFactoryConfigurer config,
            Environment env
    ) {
        config.addConnectionFactory(
                new FacebookConnectionFactory(
                        env.getProperty("facebook.app.id"),
                        env.getProperty("facebook.app.secret")
                )
        );
    }

    @Override
    public UsersConnectionRepository getUserConnectionRepository(
            ConnectionFactoryLocator connectionFactoryLocator
    ) {
        InMemoryUsersConnectionRepository repository =
                new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        return repository;
    }
    
    @Override
    public UserIdSource getUserIdSource() {
        return new SecurityContextUserIdSource();
    }
}
