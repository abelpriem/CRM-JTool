import session from '../helpers/auth/session.js'

export default function logout() {
    const req = {
        method: 'PATCH',
        headers: {
            Autorization: `Bearer ${session.token}`
        }
    }

    return fetch("http://localhost:8080/api/users/logout", req)
        .catch(error => { throw new Error(error.message)} )
        .then(res => {
            if (!res.ok) {
                return res.json()
                    .catch(error => { throw new Error(error.message) })
                    .then(body => { throw new Error(body.error) })
            }
        })
}