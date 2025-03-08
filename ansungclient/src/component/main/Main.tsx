import CustomerComponent from '../customer/CustomerComponent';
import Menu from '../menu/Menu';
import style from './main.module.css'
const Main:React.FC = () =>{
    return(
        <div id={style.main}>
            <Menu/>
            <CustomerComponent/>
        </div>
    )
}
export default Main;