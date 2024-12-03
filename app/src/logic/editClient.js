import session from '../helpers/auth/session.js'

export default function editClient(clientId, name, email, company, phone) {
    const req = {
        method: 'PATCH',
        headers: {
            Authorization: `Bearer ${session.token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, email, company, phone})
    }

    return fetch(`http://localhost:8080/api/users/clients/edit/${clientId}`, req)
        .catch(error => {throw new Error(error.message)})
        .then(res => {
            if (!res.ok) {
                return res.json()
                    .catch(error => {throw new Error(error.message)})
                    .then(body => {throw new Error(body.message)})
            }
        })
}