import { useState } from 'react'
import session from '../helpers/auth/session.js'
import { Client } from './index.js'

export default function Clients() {
    const [clients, setClients] = useState([])

    return <>
        <h2 className='font-extrabold mt-4 my-5 text-2xl'>Clientes</h2>

        {session.rol === "admin" && (
                <a href="/nuevo-cliente" className="flex items-center w-40 bg-green-600 text-white py-2 px-4 rounded-lg hover:bg-green-800">
                    <i className="fa fa-plus mr-2"></i>Nuevo Cliente</a>
            )}
        <ul>
            <Client />
        </ul>
    </>
}