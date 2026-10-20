/*
 * Copyright 2026 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.insurance.reinsurance.models.config;

import com.firefly.core.insurance.reinsurance.interfaces.enums.TreatyType;
import com.firefly.core.insurance.reinsurance.interfaces.enums.TreatyStatus;
import com.firefly.core.insurance.reinsurance.interfaces.enums.LineOfBusiness;
import com.firefly.core.insurance.reinsurance.interfaces.enums.CessionStatus;
import com.firefly.core.insurance.reinsurance.interfaces.enums.ReinsuranceRecoveryStatus;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.client.SSLMode;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers every PostgreSQL enum used by this service with the R2DBC codec.
 * Without this, the driver binds enum parameters as varchar and Postgres rejects the cast.
 */
@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Value("${DB_HOST:localhost}")
    private String host;

    @Value("${DB_PORT:5432}")
    private int port;

    @Value("${DB_NAME:postgres}")
    private String database;

    @Value("${DB_USERNAME:postgres}")
    private String username;

    @Value("${DB_PASSWORD:postgres}")
    private String password;

    @Value("${DB_SSL_MODE:disable}")
    private String sslMode;

    @WritingConverter
    static class TreatyTypeConverter implements Converter<TreatyType, TreatyType> {
        @Override
        public TreatyType convert(TreatyType source) {
            return source;
        }
    }

    @WritingConverter
    static class TreatyStatusConverter implements Converter<TreatyStatus, TreatyStatus> {
        @Override
        public TreatyStatus convert(TreatyStatus source) {
            return source;
        }
    }

    @WritingConverter
    static class LineOfBusinessConverter implements Converter<LineOfBusiness, LineOfBusiness> {
        @Override
        public LineOfBusiness convert(LineOfBusiness source) {
            return source;
        }
    }

    @WritingConverter
    static class CessionStatusConverter implements Converter<CessionStatus, CessionStatus> {
        @Override
        public CessionStatus convert(CessionStatus source) {
            return source;
        }
    }

    @WritingConverter
    static class ReinsuranceRecoveryStatusConverter implements Converter<ReinsuranceRecoveryStatus, ReinsuranceRecoveryStatus> {
        @Override
        public ReinsuranceRecoveryStatus convert(ReinsuranceRecoveryStatus source) {
            return source;
        }
    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converters = new ArrayList<>();
        converters.add(new TreatyTypeConverter());
        converters.add(new TreatyStatusConverter());
        converters.add(new LineOfBusinessConverter());
        converters.add(new CessionStatusConverter());
        converters.add(new ReinsuranceRecoveryStatusConverter());
        return converters;
    }

    @Bean
    @Primary
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .username(username)
                        .password(password)
                        .database(database)
                        .sslMode(SSLMode.valueOf(sslMode.toUpperCase()))
                        .codecRegistrar(EnumCodec.builder()
                                .withEnum("treaty_type_enum", TreatyType.class)
                                .withEnum("treaty_status_enum", TreatyStatus.class)
                                .withEnum("line_of_business_enum", LineOfBusiness.class)
                                .withEnum("cession_status_enum", CessionStatus.class)
                                .withEnum("reinsurance_recovery_status_enum", ReinsuranceRecoveryStatus.class)
                                .build())
                        .build()
        );
    }
}
