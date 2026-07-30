package com.nfctag.features.auth;

/** Demande de changement de mot de passe, indépendante de la couche web. */
public record PasswordChange(String currentPassword, String newPassword) {}