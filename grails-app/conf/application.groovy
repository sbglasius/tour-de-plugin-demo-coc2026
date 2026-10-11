/*
 * Every plugin used in this talk is already on the classpath at the `start`
 * tag, so nothing has to be downloaded part way through a live demo.
 *
 * Spring Security is the one that changes behaviour merely by being present,
 * so it is switched off here and turned on in step 1.
 */
grails.plugin.springsecurity.active = true


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'demo.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'demo.UserRole'
grails.plugin.springsecurity.authority.className = 'demo.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
		[pattern: '/',               access: ['permitAll']],
		[pattern: '/error',          access: ['permitAll']],
		[pattern: '/index',          access: ['permitAll']],
		[pattern: '/index.gsp',      access: ['permitAll']],
		[pattern: '/shutdown',       access: ['permitAll']],
		[pattern: '/assets/**',      access: ['permitAll']],
		[pattern: '/**/js/**',       access: ['permitAll']],
		[pattern: '/**/css/**',      access: ['permitAll']],
		[pattern: '/**/images/**',   access: ['permitAll']],
		[pattern: '/**/favicon.ico', access: ['permitAll']],

		// Plugin-supplied front-end libraries: jQuery, Bootstrap and the whole
		// CodeMirror 6 module graph. The web console does not work without them,
		// and the generated '/**/js/**' rule only covers some of the paths.
		[pattern: '/webjars/**',     access: ['permitAll']],
		// The web-console runs arbitrary Groovy against the live application.
		// securing it with ROLE_ADMIN.
		[pattern: '/console/**',     access: ['ROLE_ADMIN']],
		// Same lesson as the console: every plugin that adds a URL needs a rule.
		// The GreenMail inbox shows everything the application has sent.
		[pattern: '/greenmail/**',   access: ['ROLE_ADMIN']]

]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

// ROLE_ADMIN implies ROLE_USER, so admins do not need both roles assigned.
grails.plugin.springsecurity.roleHierarchy = '''
    ROLE_ADMIN > ROLE_USER
'''


// Spring Security 7 ships no default password encoder: an unprefixed password
// is rejected outright at login. s2-quickstart's User.encodePassword() is a
// no-op unless an encoder bean exists, so one has to be configured explicitly.
grails.plugin.springsecurity.password.algorithm = 'bcrypt'
