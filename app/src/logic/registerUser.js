
export default function registerUser(username, email, password, repeatPassword) {
    const req = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, email, password, repeatPassword})
    }

    return fetch('http://localhost:8080/api/users/register', req)
        .catch(error => { throw new Error(error.message) })
        .then(res => {
            if (!res.ok) {
                return res.json()
                    .catch(error => { throw new Error(error.message)} )
                    .then(body => { throw new Error(body.message) })
            }
        })
}