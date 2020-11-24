package com.kpilszak.springsocialfacebook;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
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

    @Bean
    public ConnectController connectController(
            ConnectionFactoryLocator factoryLocator,
            ConnectionRepository repository
    ) {
        ConnectController controller =
                new ConnectController(factoryLocator, repository);
        return controller;
    }

    @Bean
    public ProviderSignInController signInController(
            ConnectionFactoryLocator factoryLocator,
            UsersConnectionRepository repository
    ) {
        ProviderSignInController controller = new ProviderSignInController(
                factoryLocator,
                repository,
                new UserCookieSignInAdapter()
        );
        return controller;
    }

    @Bean
    public CanvasSignInController canvasSignInController(
            ConnectionFactoryLocator factoryLocator,
            UsersConnectionRepository repository,
            Environment env
    ) {
        return new CanvasSignInController(
                factoryLocator,
                repository,
                new UserCookieSignInAdapter(),
                env.getProperty("facebook.app.id"),
                env.getProperty("facebook.app.secret"),
                env.getProperty("facebook.app.canvas")
        );
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public Facebook facebook(ConnectionRepository repository) {
        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
        return connection != null ? connection.getApi() : null;
    }
}
