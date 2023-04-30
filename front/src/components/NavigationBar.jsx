import React from 'react';
import { Navbar, Nav } from 'react-bootstrap'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHome, faUser } from '@fortawesome/free-solid-svg-icons'
import { useNavigate, Link } from 'react-router-dom';
import BackendService from '../services/BackendService';
import Utils from '../utils/Utils';
import './NavbarStyle.css'

class NavigationBarClass extends React.Component {

    constructor(props) {
        super(props);
    }

    logout() {
        BackendService.logout().then(() => {
            Utils.removeUser();
            this.goHome()
        });
    }

    render() {
        let uname = Utils.getUserName();
        return (
            <Navbar bg="light" expand="lg">
                <Navbar.Brand className="ms-2 me-1"><FontAwesomeIcon icon={faHome} size='xs' />{' '}РПО</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/home">Главная</Nav.Link>
                    </Nav>
                        <Navbar.Text className='me-2'>{uname}</Navbar.Text>
                        { uname && <Nav.Link className="me-2" onClick={this.logout}><FontAwesomeIcon icon={faUser} fixedWidth />{' '}Выход</Nav.Link>}
                        { !uname && <Nav.Link className="me-2" as={Link} to="/login"><FontAwesomeIcon icon={faUser} fixedWidth />{' '}Вход</Nav.Link>}
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

const NavigationBar = props => {
    const navigate = useNavigate()
    return <NavigationBarClass navigate={navigate} {...props} />
}

export default  NavigationBar;