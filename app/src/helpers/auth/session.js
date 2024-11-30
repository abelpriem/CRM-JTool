const session = {
    set sessionUserId(userId) {
        if (userId) {
            sessionStorage.userId = userId
        } else {
            delete sessionStorage.userId
        }
    },

    get sessionUserId() {
        return sessionStorage.userId ? sessionStorage.userId : null
    },

    set token(token) {
        if (token) {
            sessionStorage.token = token
        } else {
            delete sessionStorage.token
        }
    },

    get token() {
        return sessionStorage.token ? sessionStorage.token : null
    },

    set username(username) {
        if (username) {
            sessionStorage.username = username
        } else {
            delete sessionStorage.username
        }
    },

    get username() {
        return sessionStorage.username ? sessionStorage.username : null
    },
}

export default session