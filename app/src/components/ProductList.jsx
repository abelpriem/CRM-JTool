import { useNavigate } from 'react-router-dom'
import Swal from 'sweetalert2'
import session from '../helpers/auth/session.js'
import { deleteProduct } from '../logic/index.js'

export default function ProductList(props) {
    const products = props.products
    const setProducts = props.setProducts
    const navigate = useNavigate()

    async function handleDeleteProduct(productId) {
        try {
            Swal.fire({
                title: "¿Desea eliminar el producto seleccionado?",
                text: "No se podrán revertir los cambios...",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Eliminar"
            }).then(async (result) => {
                if (result.isConfirmed) {
                    try {
                        await deleteProduct(productId)
                        setProducts(prevProducts => prevProducts.filter(product => product.productId !== productId))

                        Swal.fire({
                            title: "Eliminado",
                            text: "¡El producto ha sido eliminado con éxito!",
                            icon: "success"
                        })

                        window.scrollTo({
                            top: 0,
                            behavior: 'smooth'
                        })

                    } catch (error) {
                        Swal.fire({
                            title: "Error",
                            text: "Hubo un problema al eliminar el producto. Por favor, vuelva a intentarlo",
                            icon: "error"
                        })
                    }
                }
            })
        } catch (error) {
            navigate("/404")
        }
    }

    return <>
        {products.map(product => (
            <li className="mb-10" key={product.productId}>
                <div className="info-producto">
                    <p className="text-2xl font-bold text-blue-800 ml-12">{product.description}</p>
                    {product.image ? (
                        <img src={`/products/${product.image}`} alt="imagen" />
                    ) : null}
                    <div className='items-center flex space-x-3'>
                        <p className="text-xl font-bold">{product.price}€</p>
                        <p className="text-xl font-thin">{product.stock} unidades</p>
                    </div>
                </div>
                {session.rol == "admin" && (
                    <div className="flex space-x-3 mt-3">
                        <a href={"/404"} className="flex items-center w-40 bg-blue-600 text-white py-2 px-4 rounded-lg">
                            <i className="fas fa-pen-alt mr-7"></i>Editar</a>

                        <button
                            type="button"
                            className="flex items-center w-40 bg-red-600 text-white py-2 px-4 rounded-lg"
                            onClick={() => handleDeleteProduct(product.productId)}
                        ><i className="fas fa-times mr-7"></i>Eliminar</button>
                    </div>
                )}
            </li>
        ))}
    </>
}