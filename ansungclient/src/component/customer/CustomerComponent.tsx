import { useEffect, useState } from 'react';
import style from './customer.module.css'
import CustomerViewer from './CustomerViewer';
import CustomerElement from './CustomerElement';
import NewCustomer from './newcustomer/NewCustomer';
import useCustomer from '../../hook/useNewCustomer';
import { Customer } from '../../entity/Customer';


const CustomerComponent:React.FC = ()=>{
    const [modalVisible , setModalVisible] = useState<boolean>(false);
    const {getCustomer ,customers} = useCustomer();
    const [selectedCustomer ,setSelectedCustomer] = useState<Customer|undefined>();
    const [searchTel , setSearchTel] = useState<string>('');
    const [filterCustomers,setFilterCustomers] = useState<Customer[]>([]);
    useEffect(()=>{getCustomer();},[])
    
    useEffect(()=>{

    },[searchTel])
    return(
        <div id={style.customer}>
            <div id={style.customer_header}>
                <h2>고객관리</h2>
                <input id={style.search} placeholder='전화번호로 검색'></input>
            </div>
            <div id={style.customer_container}>
                <div id={style.customer_list}>
                    <div onClick={e=>setModalVisible(true)}>추가하기</div>
                    {
                        customers.map((customer)=>(<div onClick={()=>setSelectedCustomer(customer)}><CustomerElement customer={customer}/></div>))
                    }
                </div>
                <CustomerViewer selectedCustomer={selectedCustomer}/>
            </div>
            {modalVisible && <NewCustomer setVisible={setModalVisible}/>}
        </div>
    )
}
export default CustomerComponent;