import { useEffect, useState } from 'react';
import NaverMap, { LatLng } from '../../map/NaverMap';
import style from './newcustomer.module.css'
import axios from 'axios';
import useSearchLocation from '../../../hook/useSearchLocation';
import useCustomer from '../../../hook/useNewCustomer';
import { Customer } from '../../../entity/Customer';

interface Props{
    setVisible:React.Dispatch<React.SetStateAction<boolean>>;
}

const NewCustomer:React.FC<Props> = ({setVisible})=>{
    const [location,setLocation] = useState<LatLng | null>(null);
    const {searchLocation ,locations , setSelectedLocation , selectedLocation} = useSearchLocation();
    const {postNewCustomer} = useCustomer();
    const [searchAdress , setSearchAdress] = useState<string>('');

    const [tel,setTel] = useState<string>('');
    const [address,setAddress] = useState<string>('');
    const [remarks,setRemarks] = useState<string>('');

    const searchInputHandler = (e:React.ChangeEvent<HTMLInputElement>) =>{
        setSearchAdress(e.target.value);
    }
    const getSearchLocation = () =>{
        searchLocation(searchAdress);
    }
    const saveCustomerHandler = () =>{
        const newCustomer:Customer={
            tel: tel,
            address:address,
            remarks:remarks,
            lat: selectedLocation?.LatLng.Lat,
            lng: selectedLocation?.LatLng.Lng
        }
        postNewCustomer(newCustomer);
        console.log(newCustomer)
        setVisible(false);
    }
    const telOnchangeHanlder = (e:React.ChangeEvent<HTMLInputElement>) =>{
        setTel(e.target.value);
    }
    const addressOnchangeHandler = (e:React.ChangeEvent<HTMLInputElement>) =>{
        setAddress(e.target.value);
    }
    const remarksOnchangeHandler = (e:React.ChangeEvent<HTMLInputElement>) =>{
        setRemarks(e.target.value);
    }
    useEffect(()=>{
        if(selectedLocation){
            setAddress(selectedLocation.address);
        }
    },[selectedLocation])
    return(
        <div id={style.background} onClick={(e)=>{setVisible(false)}}>
            <div id={style.modal} onClick={(e)=>{e.stopPropagation();}}>
                <h2>고객 추가</h2>
                <input placeholder='주소검색' onChange={(e)=>searchInputHandler(e)}></input><button onClick={getSearchLocation}>검색</button>
                <NaverMap location={location} locations={locations} setSelectLocation={setSelectedLocation}/>
                <div id={style.form}>
                    <input placeholder='전화번호' value={tel} onChange={e=>telOnchangeHanlder(e)}></input>
                    <input placeholder='주소' value={address} onChange={e=>addressOnchangeHandler(e)}></input>
                    <input placeholder='상세정보' value={remarks} onChange={e=>remarksOnchangeHandler(e)}></input>
                </div>
                <button onClick={saveCustomerHandler}>고객정보 저장</button>
            </div>
        </div>
    )
}
export default NewCustomer;