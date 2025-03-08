import style from './menu.module.css'
const Menu:React.FC = () =>{
    return(
        <div id={style.menu}>
            <div className={style.element}>
                고객 관리
            </div>
            <div className={style.element}>
                상품 관리
            </div>
            <div className={style.element}>
                판매 기록
            </div>
            <div className={style.element}>
                배달 관리
            </div>
            <div className={style.element}>
                발주 관리
            </div>
            <div className={style.element}>
                판매
            </div>
        </div>
    )
}
export default Menu;