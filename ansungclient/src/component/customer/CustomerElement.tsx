import { Customer } from '../../entity/Customer';
import style from './customerElement.module.css'

interface Props{
    customer:Customer
}

const CustomerElement:React.FC<Props> = ({customer}) =>{
    return(
        <div className={style.customer_element}>
            <div className={style.customer_info}>
                <div className={style.tel}>{customer.tel}</div>
                <div className={style.address}>{customer.address}</div>
                <div className={style.remarks}>{customer.remarks}</div>
            </div>
        </div>
    )
}
export default CustomerElement;