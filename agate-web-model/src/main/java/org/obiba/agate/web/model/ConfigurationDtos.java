package org.obiba.agate.web.model;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.LocaleUtils;
import org.obiba.agate.domain.AttributeConfiguration;
import org.obiba.agate.domain.Configuration;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
class ConfigurationDtos {

  @NotNull
  Agate.ConfigurationDto asDto(@NotNull Configuration configuration) {
    Agate.ConfigurationDto.Builder builder = Agate.ConfigurationDto.newBuilder() //
      .setName(configuration.getName()) //
      .setShortTimeout(configuration.getShortTimeout()) //
      .setLongTimeout(configuration.getLongTimeout())//
      .setInactiveTimeout(configuration.getInactiveTimeout()) //
      .setJoinWithUsername(configuration.isJoinWithUsername());

    configuration.getLocales().forEach(locale -> builder.addLanguages(locale.getLanguage()));

    if(configuration.hasDomain()) builder.setDomain(configuration.getDomain());
    if(configuration.hasPublicUrl()) builder.setPublicUrl(configuration.getPublicUrl());
    if(configuration.hasUserAttributes())
      configuration.getUserAttributes().forEach(c -> builder.addUserAttributes(asDto(c)));
    if(configuration.getAgateVersion() != null) {
      builder.setVersion(configuration.getAgateVersion().toString());
    }
    if(configuration.hasStyle()) builder.setStyle(configuration.getStyle());
    return builder.build();
  }

  @NotNull
  Configuration fromDto(@NotNull Agate.ConfigurationDtoOrBuilder dto) {
    Configuration configuration = new Configuration();
    configuration.setName(dto.getName());
    if(dto.hasDomain()) configuration.setDomain(dto.getDomain());
    if(dto.hasPublicUrl()) configuration.setPublicUrl(dto.getPublicUrl());
    dto.getLanguagesList().forEach(lang -> configuration.getLocales().add(LocaleUtils.toLocale(lang)));
    configuration.setShortTimeout(dto.getShortTimeout());
    configuration.setLongTimeout(dto.getLongTimeout());
    configuration.setInactiveTimeout(dto.getInactiveTimeout());
    configuration.setJoinWithUsername(dto.getJoinWithUsername());
    if(dto.getUserAttributesCount() > 0)
      dto.getUserAttributesList().forEach(c -> configuration.addUserAttribute(fromDto(c)));
    if(dto.hasStyle()) configuration.setStyle(dto.getStyle());
    return configuration;
  }

  @NotNull
  private Agate.AttributeConfigurationDto.Builder asDto(AttributeConfiguration config) {
    Agate.AttributeConfigurationDto.Builder builder = Agate.AttributeConfigurationDto.newBuilder()
      .setName(config.getName()).setRequired(config.isRequired()).setType(config.getType().toString())
      .addAllValues(config.getValues());

    if(config.hasDescription()) {
      builder.setDescription(config.getDescription());
    }

    return builder;
  }

  @NotNull
  private AttributeConfiguration fromDto(Agate.AttributeConfigurationDto config) {
    AttributeConfiguration attributeConfiguration = new AttributeConfiguration();
    attributeConfiguration.setName(config.getName());
    attributeConfiguration.setType(config.getType());
    attributeConfiguration.setRequired(config.getRequired());
    attributeConfiguration.setValues(config.getValuesList());
    attributeConfiguration.setDescription(config.hasDescription() ? config.getDescription() : null);
    return attributeConfiguration;
  }

}
