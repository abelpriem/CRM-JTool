import errors from '../helpers/errors/errors.js'
const { SystemError } = errors

export default function registerUser(username, email, password, repeatPassword) {
    const req = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, email, password, repeatPassword})
    }

    return fetch('http://localhost:8080/api/users', req)
        .catch(error => { throw new SystemError(error.message) })
        .then(res => {
            if (!res.ok) {
                return res.json()
                    .catch(error => { throw new SystemError(error.message)} )
                    .then(body => { throw new errors[body.error](body.message)} )
            }
        })
}