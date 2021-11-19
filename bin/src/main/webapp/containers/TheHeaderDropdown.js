import React,{useState, useEffect} from 'react'
import axios from "axios";
import {url as baseUrl} from "./../api";
import {
  CBadge,
  CDropdown,
  CDropdownItem,
  CDropdownMenu,
  CDropdownToggle,
  CImg
} from '@coreui/react'
import CIcon from '@coreui/icons-react';
import {  toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { CgProfile } from 'react-icons/cg';

import * as ACTION_TYPES from "./../actions/types";
import store from "./../store";
import { useHistory } from "react-router-dom";
import { authentication } from "./../_services/authentication";

const { dispatch } = store;

const TheHeaderDropdown = () => {
  let history = useHistory();
  const [isOpenNotificationPopover, setIsOpenNotificationPopover] = useState(false);
  const [isNotificationConfirmed, setIsNotificationConfirmed] = useState(false);
  const [isOpenUserCardPopover, setIsOpenUserCardPopover] = useState(false);
  const [user, setUser] = useState(null);
  const [modal, setModal] = useState(false);

  const toggleNotificationPopover = () => {
    setIsOpenNotificationPopover(!isOpenNotificationPopover);

    if (!isNotificationConfirmed) {
      setIsNotificationConfirmed(true);
    }
  };

  const toggleUserCardPopover = () => {
    setIsOpenUserCardPopover(!isOpenUserCardPopover);
  };

  const toggleAssignFacilityModal = () => {
    setModal(!modal);
  };

  const handleSidebarControlButton = (event) => {
    event.preventDefault();
    event.stopPropagation();

    document.querySelector(".cr-sidebar").classList.toggle("cr-sidebar--open");
  };

  const logout = () => {
    history.push("/login");
    authentication.logout();
  }

  async function fetchMe() {
    if( authentication.currentUserValue != null ) {
      axios
          .get(`${baseUrl}account`)
          .then((response) => {
            setUser(response.data);
            console.log(response.data);
            // set user permissions in local storage for easy retrieval, when user logs out it will be removed from the local storage
            localStorage.setItem('currentUser_Permission', JSON.stringify(response.data.permissions));
            dispatch({
              type: ACTION_TYPES.FETCH_PERMISSIONS,
              payload: response.data.permissions,
            });
          })
          .catch((error) => {
            authentication.logout();
           // console.log(error);
          });
    }
  }

  async function switchFacility (facility) {
    console.log(facility)
    await axios.post(`${baseUrl}users/organisationUnit/${facility.value.organisationUnitId}`, {})
        .then(response => {
          toast.success('Facility switched successfully!');
          fetchMe();
          toggleAssignFacilityModal();
        }) .catch((error) => {
         toast.error('An error occurred, could not switch facilty.');
        });

  }

  const currentUser = authentication.getCurrentUser();
  useEffect(() => {
    fetchMe();
  }, []);

  return (
    <>
    <CDropdown
      inNav
      className="c-header-nav-items mx-2"
      direction="down"
    >
      <CDropdownToggle className="c-header-nav-link" caret={false}>
        <div className="c-avatar">
          <CgProfile className="c-avatar-img"/>
        </div>
        
      </CDropdownToggle>
      <CDropdownMenu className="pt-0" placement="bottom-end">
        
        <CDropdownItem
          header
          tag="div"
          color="light"
          className="text-center"
        >
          
        </CDropdownItem>
        <CDropdownItem >
          <CIcon name="cil-user" className="mfe-2" />{user && user!==null ? user.userName : ""}
        </CDropdownItem>

        <CDropdownItem divider />
        <CDropdownItem style={{ color:'red', fontSize:'bold'}} onClick={logout}>
          <CIcon name="cil-lock-locked" className="mfe-2" />
          Log out
        </CDropdownItem>
      </CDropdownMenu>
    </CDropdown>

    </>
  )
}

export default TheHeaderDropdown
