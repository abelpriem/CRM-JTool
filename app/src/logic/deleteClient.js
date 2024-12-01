import session from '../helpers/auth/session.js'

export default function deleteClient(clientId) {
    const req = {
        method: 'DELETE',
        headers: {
            Authorization: `Bearer ${session.token}`
        }
    }

    return fetch(`http://localhost:8080/api/clients/edit/${clientId}`, req)
        .catch(error => {throw new Error(error.message)})
        .then(res => {
            if (!res.ok) {
                return res.json()
                    .catch(error => {throw new Error(error.message)})
                    .then(body => {throw new Error(body.message)})
            }
        })
}