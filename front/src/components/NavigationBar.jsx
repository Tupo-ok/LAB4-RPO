import React from 'react';
import { Navbar, Nav } from 'react-bootstrap'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faBars, faHome, faUser } from '@fortawesome/free-solid-svg-icons'
import { useNavigate, Link } from 'react-router-dom';
import BackendService from '../services/BackendService';
import Utils from '../utils/Utils';
import { userActions } from '../utils/Rdx';
import { connect } from 'react-redux';
import './NavbarStyle.css'

class NavigationBarClass extends React.Component {

    constructor(props) {
        super(props);
        this.logout = this.logout.bind(this)
    }

    
    goHome() {
        this.props.navigate('/home');
    }

    logout() {
        BackendService.logout().then(() => {
            Utils.removeUser();
            this.props.dispatch(userActions.logout());
            this.props.navigate("/login");
        });
    }

    render() {
        let uname = Utils.getUserName();
        return (
            <Navbar bg="dark" expand="lg" variant="dark">
                <button type="button" className="btn btn-outline-secondary mr-2" onClick={this.props.toggleSideBar}>
                    <FontAwesomeIcon icon={faBars}/>
                </button>
                <Navbar.Brand className="ms-2 me-1">{' '}РПО</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/home">Главная</Nav.Link>
                    </Nav>
                    <Navbar.Text>{this.props.user && this.props.user.login}</Navbar.Text>
                    { this.props.user && <Nav.Link onClick={this.logout}><FontAwesomeIcon icon={faUser} fixedWidth />{' '}Выход</Nav.Link>}
                    { !this.props.user && <Nav.Link as={Link} to="/login"><FontAwesomeIcon icon={faUser} fixedWidth />{' '}Вход</Nav.Link>}
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

const mapStateToProps = state => {
    const { user } = state.authentication;
    return { user };
}

const NavigationBar = props => {
    const navigate = useNavigate()
    return <NavigationBarClass navigate={navigate} {...props} />
}

export default  connect(mapStateToProps)(NavigationBar);