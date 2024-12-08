import session from '../helpers/auth/session.js'

export default function retrieveProducts() {
    const req = {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${session.token}`
        }
    }

    return fetch("http://localhost:8080/api/products", req)
        .catch(error => {throw new Error(error.message)})
        .then(res => {
            if (!res.ok) {
                return res.json()
                    .catch(error => {throw new Error(error.message)})
                    .then(body => {throw new Error(body.message)})
            }

            return res.json()
                .catch(error => {throw new Error(error.message)})
                .then(products => {
                    return products
                })
        })
}