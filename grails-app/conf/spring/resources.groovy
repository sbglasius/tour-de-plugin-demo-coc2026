import org.springframework.security.crypto.factory.PasswordEncoderFactories

// Place your Spring DSL code here
beans = {

    /*
     * Spring Security 7 has no default password encoder, and s2-quickstart's
     * generated User.encodePassword() silently does nothing unless a
     * `passwordEncoder` bean exists -- which stores the password in plain text
     * and then fails at login with:
     *
     *   "there is no default password encoder configured, each password must
     *    have a password encoding prefix"
     *
     * The delegating encoder writes a `{bcrypt}` prefix on encode and reads the
     * prefix back on match, which is what Spring Security 7 expects.
     */
    passwordEncoder(PasswordEncoderFactories) { bean ->
        bean.factoryMethod = 'createDelegatingPasswordEncoder'
    }

}