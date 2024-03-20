import './HeroComponent.scss';
import MainTagline from "./MainTagline";
import SubTagline from './SubTagline';

const HeroComponent = () => {
    return (
        <div className="hero-component">
            <MainTagline />
            <SubTagline />
        </div>
    )
}

export default HeroComponent