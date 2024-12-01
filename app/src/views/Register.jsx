import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { registerUser } from '../logic/index.js'

export default function Register() {
    const [showError, setShowError] = useState('')
    const navigate = useNavigate()

    async function handleSubmitRegister(event) {
        event.preventDefault()

        setShowError('')

        const username = event.target.username.value
        const email = event.target.email.value
        const password = event.target.password.value
        const repeatPassword = event.target.repeatPassword.value

        try {
            await registerUser(username, email, password, repeatPassword)
            navigate('/login')
        } catch(error) {
            setShowError(error.message)
        }
    }

    return <>
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
            <h1 className="text-2xl font-bold mb-8">JTool - CRM</h1>

            {showError && (
                <div className="font-bold text-red-600">{showError}</div>
            )}

            <form onSubmit={handleSubmitRegister} className="flex flex-col space-y-3 w-80 p-3">
                <label className="text-sm font-bold">Nombre Usuario</label>
                <input className="p-2 border border-gray-300 rounded-md" type="text" name="username" placeholder="Introduce tu nombre" required />

                <label className="text-sm font-bold">Email</label>
                <input className="p-2 border border-gray-300 rounded-md" type="email" name="email" placeholder="Introduce tu email" required />

                <label className="text-sm font-bold">Contraseña</label>
                <input className="p-2 border border-gray-300 rounded-md" type="password" name="password" placeholder="Introduce tu contraseña "required />

                <label className="text-sm font-bold">Repetir</label>
                <input className="p-2 border border-gray-300 rounded-md" type="password" name="repeatPassword" placeholder="Repite la contraseña "required />

                <button className="bg-sky-900 text-white py-2 px-4 rounded-md hover:bg-sky-500" type="submit" value="Create account">Crear Cuenta</button>

                <a href="/login" className="font-bold underline">¡Vuelve a iniciar sesión!</a>
            </form>
        </div>
    </>
}