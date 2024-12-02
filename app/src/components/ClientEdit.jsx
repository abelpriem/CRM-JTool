import { useState } from "react"

export default function ClientEdit() {
    const [showError, setShowError] = useState()

    return <>
        <h2 className='font-extrabold mt-4 my-5 text-2xl'>Editar clientes</h2>

        {showError && (
            <div className="font-bold text-red-600">{showError}</div>
        )}


    </>
}