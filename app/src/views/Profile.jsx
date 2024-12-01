import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Header } from '../components/index.js'
import session from '../helpers/auth/session.js'
import { retrieveUser } from '../logic/index.js'

export default function Profile() {
    const [user, setUser] = useState([])
    const [showError, setShowError] = useState('')
    const navigate = useNavigate()

    useEffect(() => {
        async function fetchUser() {
            try {
                const user = await retrieveUser()
                setUser(user[0])
            } catch(error) {
                setShowError(error.message)
            }
        }

        fetchUser()
    }, [])

    async function handlePasswordChange(event) {
        event.preventDefault()

        const currentPassword = event.target.currentPassword.value
        const newPassword = event.target.newPassword.value
        const repeatPassword = event.target.repeatPassword.value

        try {
            // TODO
        } catch(error) {
            setShowError(error.message)
        }
    }

    return (
        <div className="bg-gray-200 min-h-screen">
        <Header />

        <div className="bg-white rounded ml-40 mt-8 mr-32 mb-8 p-10 flex-1 min-h-[calc(100vh-4rem)]">
            <h1 className="text-center font-extrabold text-5xl mb-10">Perfil</h1>

            <div className="flex">
                <div className="w-1/2 pr-8 border-r border-gray-300">
                    <h2 className="text-3xl font-semibold mb-6">Datos del Usuario</h2>
                    <p className="text-xl font-semibold">Nombre: {user.username}</p>
                    <p className="text-xl mt-4 font-semibold">Empresa: {user.company}</p>
                    <p className={`text-xl mt-4 font-semibold ${session.rol === "admin" ? "text-red-600" : "text-blue-600"}`}>
                        Rol: {session.rol === "admin" ? "Administrador" : "Usuario"}
                    </p>
                    <p className="text-xl mt-4 font-semibold">Email: {user.email}</p>
                    <p className="text-xl mt-4 font-semibold">Tel: {user.phone}</p>
                </div>

                <div className="w-1/2 pl-8">
                    <h2 className="text-3xl font-semibold mb-6">Cambiar Contraseña</h2>

                    {showError && (
                        <div className="font-bold text-red-600">{showError}</div>
                    )}

                    <form onSubmit={handlePasswordChange} className="space-y-6">
                        <div>
                            <label htmlFor="currentPassword" className="block text-lg font-semibold">Contraseña Actual</label>
                            <input
                                type="password"
                                id="currentPassword"
                                name="currentPassword"
                                className="w-full p-3 border border-gray-300 rounded-md"
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="newPassword" className="block text-lg font-semibold">Nueva Contraseña</label>
                            <input
                                type="password"
                                id="newPassword"
                                name="newPassword"
                                className="w-full p-3 border border-gray-300 rounded-md"
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="confirmPassword" className="block text-lg font-semibold">Confirmar Nueva Contraseña</label>
                            <input
                                type="password"
                                id="confirmPassword"
                                name="confirmPassword"
                                className="w-full p-3 border border-gray-300 rounded-md"
                                required
                            />
                        </div>

                        <button
                            type="submit"
                            className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition duration-200"
                        >
                            Cambiar Contraseña
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    )
}
