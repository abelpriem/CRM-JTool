import { useEffect, useState } from 'react'
import session from '../helpers/auth/session.js'

export default function Orders() {
    const [orders, setOrders] = useState([])
    const [showError, setShowError] = useState('')

    useEffect(() => {
        async function fetchListOrders() {
            try {
                // const ordersList = await retrieveOrders()
                // setClients(ordersList)
            } catch (error) {
                // setShowError(error.message)
            }
        }

        fetchListOrders()
    }, [])

    return <>
        <h2 className='font-extrabold mt-4 my-5 text-2xl'>Pedidos</h2>

        {showError && (
            <div className="font-bold text-red-600">{showError}</div>
        )}

        {session.rol === "admin" && (
            <a href="/404" className="flex items-center w-40 bg-green-600 text-white py-2 px-4 rounded-lg hover:bg-green-800">
                <i className="fa fa-plus mr-2"></i>Nuevo Pedido</a>
        )}

        <p className='font-bold mt4 my-5 text-xl'>No hay pedidos creados en la base de datos</p>

    </>
}