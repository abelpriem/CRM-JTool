import { useEffect, useState } from 'react'
import session from '../helpers/auth/session.js'
import { retrieveClients } from '../logic/index.js'
import { ClientList } from './index.js'

export default function Clients() {
    const [clients, setClients] = useState([])
    const [showError, setShowError] = useState('')
    
    useEffect(() => {
        async function fetchClients() {
            try {
                const clientsList = await retrieveClients()
                setClients(clientsList)
            } catch(error) {
                setShowError(error.message)
            }
        }

        fetchClients()
    }, [])

    return <>
        <h2 className='font-extrabold mt-4 my-5 text-2xl'>Clientes</h2>

        {showError && (
            <div className="font-bold text-red-600">{showError}</div>
        )}

        {session.rol === "admin" && (
                <a href="/nuevo-cliente" className="flex items-center w-40 bg-green-600 text-white py-2 px-4 rounded-lg hover:bg-green-800">
                    <i className="fa fa-plus mr-2"></i>Nuevo Cliente</a>
            )}

        <ul>
            {clients.length
                ? <ClientList clients={clients} />
                : <p className='font-bold mt4 my-5 text-xl'>No hay clientes en la base de datos</p>
            }
        </ul>
    </>
}