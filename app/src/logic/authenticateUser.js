import session from '../helpers/auth/session.js'

export default function authenticateUser(email, password) {
    const req = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    }

    return fetch('http://localhost:8080/api/users/auth', req)
        .catch(error => { throw new Error(error.message) })
        .then(res => {
            if (!res.ok) {
                return res.json()
                    .catch(error => { throw new Error(error.message) })
                    .then(body => { throw new Error(body.message) })
            }

            return res.json()
                .catch(error => { throw new Error(error.message) })
                .then(data => {
                    session.username = data.username
                    session.token = data.token
                    session.rol = data.rol
                })
        })
 }