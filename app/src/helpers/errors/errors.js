
class NotFoundError extends Error {
    constructor(message) {
        super(message)

        this.name = this.constructor.name
    }
}

class SystemError extends Error {
    constructor(message) {
        super(message)

        this.name = this.constructor.name
    }
}

class ContentError extends Error {
    constructor(message) {
        super(message)

        this.name = this.constructor.name
    }
}

class DuplicityError extends Error {
    constructor(message) {
        super(message)

        this.name = this.constructor.name
    }
}

class CredentialsError extends Error {
    constructor(message) {
        super(message)

        this.name = this.constructor.name
    }
}

class RelationalError extends Error {
    constructor(message) {
        super(message)

        this.name = this.constructor.name
    }
}

class TokenError extends Error {
    constructor(message) {
        super(message)

        this.name = this.constructor.name
    }
}

class AuthorizationError extends Error {
    constructor(message) {
        super(message)

        this.name = this.constructor.name
    }
}

export {
    AuthorizationError, ContentError, CredentialsError, DuplicityError, NotFoundError, RelationalError, SystemError, TokenError
}

const errors = {
    NotFoundError,
    SystemError,
    ContentError,
    DuplicityError,
    CredentialsError,
    RelationalError,
    TokenError,
    AuthorizationError
}

export default errors