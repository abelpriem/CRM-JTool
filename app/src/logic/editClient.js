import session from '../helpers/auth/session.js'

export default function editClient(clientId) {
    const req = {
        method: 'PATCH',
        headers: {
            Authorization: `Bearer ${session.token}`
        }
    }

    return fetch(`http://localhost:8080/clients/edit/${clientId}`, req)
        .catch(error => {throw new Error(error.message)})
        .then(res => {
            if (!res.ok) {
                return res.json()
                    .catch(error => {throw new Error(error.message)})
                    .then(body => {throw new Error(body.message)})
            }
        })
}