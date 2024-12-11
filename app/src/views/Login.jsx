import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { authenticateUser } from '../logic/index.js'

export default function Login() {
    const [showError, setShowError] = useState('')
    const navigate = useNavigate()

    async function handleSubmitLogin(event) {
        event.preventDefault()

        setShowError('')

        const email = event.target.email.value
        const password = event.target.password.value

        try {
            await authenticateUser(email, password)
            navigate("/home")
        } catch (error) {
            setShowError(error.message)
        }
    }

    return <>
        <div className="flex flex-col items-center justify-center min-h-screen bg-white">
            <img src={"/JTool.jpg"} alt='JTool' className="w-96 h-auto mb-10" />

            {showError && (
                <div className="font-bold text-red-600">{showError}</div>
            )}

            <form onSubmit={handleSubmitLogin} className="flex flex-col space-y-3 w-80 p-3">
                <label className="text-sm font-bold">Email</label>
                <input className="p-2 border border-gray-300 rounded-md" type="email" name="email" placeholder="Introduce tu email" required />

                <label className="text-sm font-bold">Contraseña</label>
                <input className="p-2 border border-gray-300 rounded-md" type="password" name="password" placeholder="Introduce tu contraseña " required />

                <button className="bg-sky-900 text-white py-2 px-4 rounded-md hover:bg-sky-500" type="submit">Iniciar Sesión</button>

                <a href="/register" className="font-bold underline">Aun no tienes cuenta? Registrarse!</a>
            </form>
        </div>
    </>
}