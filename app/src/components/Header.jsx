import { useNavigate } from 'react-router-dom'
import session from '../helpers/auth/session.js'

export default function Header() {
    const navigate = useNavigate()

    async function handleLogout(event) {
        event.preventDefault()

        try {
            sessionStorage.clear()
            navigate('/login')
        } catch(error) {
            throw new Error(error.message)
        }
    }

    return <>
        <header className="py-5 px-3 bg-sky-900 text-lg">
            <div className="font-bold text-white flex justify-between">
                <div className="pl-16 font-extrabold">
                    <a href="/home">CRM - ADMINISTRADOR DE CLIENTES</a>
                </div>
                <div className="pr-32 space-x-4 flex items-center">
                    <div className="flex items-center mr-4">
                        <p className="font-extrabold">{`Bienvenido! ${session.username}`}</p>
                    </div>
                    <div className="space-x-4">
                        <a href="/profile">PERFIL</a>
                        <a href="/logout" onClick={handleLogout}>LOGOUT</a>
                    </div>
                </div>
            </div>
        </header>
    </>
}
