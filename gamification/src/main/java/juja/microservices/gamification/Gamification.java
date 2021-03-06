/**
 * Copyright (c) 2016, juja.com.ua
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * * Neither the name of microservices nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package juja.microservices.gamification;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import juja.microservices.gamification.user.LoginPasswordRepositoryDefault;
import juja.microservices.gamification.user.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Main entry point to application.
 *
 * @author Viktor Kuchyn (kuchin.victor@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@SpringBootApplication
@SuppressWarnings({"PMD.UseUtilityClass", "DesignForExtension"})
public class Gamification {

    /**
     * Value of host from application.properties.
     */
    @Value("${spring.data.mongodb.host}")
    private String host;

    /**
     * Value of database name from application.properties.
     */
    @Value("${spring.data.mongodb.database}")
    private String database;

    /**
     * Main method.
     * Entry point for SpringBoot application.
     *
     * @param args Application arguments
     */
    public static void main(final String... args) {
        SpringApplication.run(Gamification.class, args);
    }

    /**
     * Creation of Mongo bean.
     * @return Mongo
     */
    public @Bean Mongo mongo() {
        return new MongoClient(host);
    }

    /**
     * Creation of MongoTemplate bean.
     * @return MongoTemplate
     */
    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), database);
    }

    /**
     * Creation of UserRepositoryImpl bean.
     * @return UserRepositoryImpl
     */
    public @Bean UserRepositoryImpl userRepository() {
        return new UserRepositoryImpl(mongoTemplate());
    }

    /**
     * Creation of LoginPasswordRepositoryDefault bean.
     * @return LoginPasswordRepositoryDefault
     */
    public @Bean LoginPasswordRepositoryDefault loginPasswordRepository() {
        return new LoginPasswordRepositoryDefault(mongoTemplate());
    }
}
