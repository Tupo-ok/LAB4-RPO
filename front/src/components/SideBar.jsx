import React from 'react';
import { Link } from 'react-router-dom'
import { Nav } from 'react-bootstrap'
import {faGlobe, faUser, faShop, faCircleUser} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


const SideBar = props => {
    return (
        <>
        { props.expanded &&
            <Nav className={"flex-column my-sidebar my-sidebar-expanded"}>
                <Nav.Item><Nav.Link as={Link} to="/countries"><FontAwesomeIcon icon={faGlobe} />{' '}Страны</Nav.Link></Nav.Item>
                <Nav.Item><Nav.Link as={Link} to="/artists"><FontAwesomeIcon icon={faUser} />{' '}Артисты</Nav.Link></Nav.Item>
                <Nav.Item><Nav.Link as={Link} to="/users"><FontAwesomeIcon icon={faCircleUser} />{' '}Юзеры</Nav.Link></Nav.Item>
                <Nav.Item><Nav.Link as={Link} to="/museums"><FontAwesomeIcon icon={faShop} />{' '}Музеи</Nav.Link></Nav.Item>
                <Nav.Item><Nav.Link as={Link} to="/paints"><FontAwesomeIcon icon={faShop} />{' '}Картины</Nav.Link></Nav.Item>
            </Nav>
        }
        { !props.expanded &&
            <Nav className={"flex-column my-sidebar my-sidebar-collapsed"}>
                <Nav.Item><Nav.Link as={Link} to="/countries"><FontAwesomeIcon icon={faGlobe} size="2x" /></Nav.Link></Nav.Item>
                <Nav.Item><Nav.Link as={Link} to="/artists"><FontAwesomeIcon icon={faUser} size="2x" /></Nav.Link></Nav.Item>
                <Nav.Item><Nav.Link as={Link} to="/users"><FontAwesomeIcon icon={faCircleUser} size="2x" /></Nav.Link></Nav.Item>
                <Nav.Item><Nav.Link as={Link} to="/museums"><FontAwesomeIcon icon={faShop} size="2x" /></Nav.Link></Nav.Item>
                <Nav.Item><Nav.Link as={Link} to="/paints"><FontAwesomeIcon icon={faShop} size="2x" /></Nav.Link></Nav.Item>
            </Nav>
        }
        </>
    )
}

export default SideBar;