import { useEffect, useState } from 'react'
import session from '../helpers/auth/session'
import { retrieveProducts } from '../logic/index.js'
import { ProductList } from './index.js'

export default function Products() {
    const [products, setProducts] = useState([])
    const [showError, setShowError] = useState('')

    useEffect(() => {
        async function fetchProducts() {
            try {
                const productsList = await retrieveProducts()
                setProducts(productsList)
            } catch (error) {
                setShowError(error.message)
            }
        }

        fetchProducts()
    }, [])

    return <>
        <h2 className='font-extrabold mt-4 my-5 text-2xl'>Productos</h2>

        {showError && (
            <div className="font-bold text-red-600">{showError}</div>
        )}

        {session.rol === "admin" && (
            <a href="/home/products/new-product" className="flex items-center w-44 bg-green-600 text-white py-2 px-4 rounded-lg hover:bg-green-800">
                <i className="fa fa-plus mr-2"></i>Nuevo Producto</a>
        )}

        <ul className='grid grid-cols-2 gap-4 ml-10 mt-10'>
            {products.length
                ? <ProductList products={products} setProducts={setProducts} />
                : <p className='font-bold mt4 my-5 text-xl'>No hay productos registrados en la base de datos</p>
            }
        </ul>
    </>
}