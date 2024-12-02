import { useState } from 'react'
import { createClient } from '../logic/index.js'

export default function NewClient() {
    const [showError, setShowError] = useState()
    const [showMessage, setShowMessage] = useState()

    async function handleSumitNewClient(event) {
        event.preventDefault()

        setShowError('')
        setShowMessage('')

        const name = event.target.name.value
        const email = event.target.email.value
        const company = event.target.company.value
        const phone = event.target.phone.value

        try {
            await createClient(name, email, company, phone)
            setShowMessage("Cliente creado correctamente")
        } catch (error) {
            setShowError(error.message)
        }
    }

    return <>
        <h2 className='font-extrabold mt-4 my-5 text-2xl'>Nuevo cliente</h2>

        {showError && (
            <div className="font-bold text-red-600">{showError}</div>
        )}

        {showMessage && (
            <div className="font-bold text-green-600 ">{showMessage}</div>
        )}

        <form onSubmit={handleSumitNewClient} className="flex flex-col space-y-3 w-80 p-3 mt-2">
            <label className="text-sm font-bold">Nombre Cliente</label>
            <input className="p-2 border border-gray-300 rounded-md" type="text" name="name" placeholder="Introduce el nombre" required />

            <label className="text-sm font-bold">Email</label>
            <input className="p-2 border border-gray-300 rounded-md" type="email" name="email" placeholder="Introduce el email" required />

            <label className="text-sm font-bold">Empresa</label>
            <input className="p-2 border border-gray-300 rounded-md" type="text" name="company" placeholder="Introduce la compañía " required />

            <label className="text-sm font-bold">Teléfono</label>
            <input className="p-2 border border-gray-300 rounded-md" type="text" name="phone" placeholder="Introduce el teléfono de contacto " required />

            <button className="bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-800" type="submit" value="Create client">Nuevo cliente</button>
        </form>

    </>
}
