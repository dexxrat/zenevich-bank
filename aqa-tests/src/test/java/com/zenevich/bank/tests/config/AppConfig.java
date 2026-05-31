package com.zenevich.bank.tests.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config.properties"})
public interface AppConfig extends Config {
    @Key("base.url")
    String baseUrl();

    @Key("auth.url")
    String authUrl();

    @Key("accounts.url")
    String accountsUrl();
}