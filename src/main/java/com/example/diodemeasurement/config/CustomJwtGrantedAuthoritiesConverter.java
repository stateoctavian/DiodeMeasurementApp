package com.example.diodemeasurement.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;

public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

		private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

		@Override
		public Collection<GrantedAuthority> convert(Jwt source) {
				Collection<GrantedAuthority> grantedAuthorities = defaultGrantedAuthoritiesConverter.convert(source);
				// You can add additional custom authorities here if needed
				return grantedAuthorities;
		}
}
