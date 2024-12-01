import session from '../helpers/auth/session.js'

export default function changePassword(password, newPassword, confirmPassword) {
    const req = {
        method: 'PATCH',
        headers: {
            Authorization: `Bearer ${session.token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ password, newPassword, confirmPassword })
    }

    return fetch("http://localhost:8080/api/users/change-password", req)
        .catch(error => {throw new Error(error.message)})
        .then(res => {
            if (!res.ok) {
                return res.json()
                    .catch(error => {throw new Error(error.message)})
                    .then(body => {throw new Error(body.message)})
            }
        })
}