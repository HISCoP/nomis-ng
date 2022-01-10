import React, {useEffect, useState} from 'react'
import {
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CRow
} from '@coreui/react'

import MaterialTable from 'material-table';
import {Menu,MenuList,MenuButton,MenuItem,} from "@reach/menu-button";
import "@reach/menu-button/styles.css";
import { Link } from 'react-router-dom';
import {connect, useDispatch} from "react-redux";
import { fetchAllHouseHoldMember } from "./../../../actions/houseHoldMember";
import {calculateAge} from "./../../../utils/calculateAge";
import NewOvc from "../household/NewOvc";
import { FaRegDotCircle } from 'react-icons/fa';

const HouseholdMember = (props) => {

  const [loading, setLoading] = useState('')
    const dispatch = useDispatch();
    React.useEffect(() => {
        //show side-menu when this page loads
        dispatch({type: 'MENU_MINIMIZE', payload: false });
    },[]);
  useEffect(() => {
  setLoading('true');
      const onSuccess = () => {
          setLoading(false)
      }
      const onError = () => {
          setLoading(false)     
      }
          props.fetchAllMember(onSuccess, onError);
  }, []); //componentDidMount



  return (
    <>
      
        <CRow>
        <CCol>
          <CCard>
            <CCardHeader>
              Household Members
            </CCardHeader>
            <CCardBody>
            <MaterialTable
                title="Household Member List"
                columns={[
                  { title: 'Unique ID', field: 'id' },
                  { title: 'Name', field: 'name' },
                  
                  { title: 'Date Assessed', field: 'date' },
                  
                  {
                    title: 'Age',
                    field: 'age',
                    
                  },
                  { title: 'Status', field: 'type' },
                  {
                    title: 'Action',
                    field: 'action',
                    
                  },
                ]}
                isLoading={loading}
                data={props.houseMemberList.map((row) => ({
                  id: <span> <Link
                      to={{pathname: "/household-member/home", state: row.id, householdId:row.householdId }}>{row.details.uniqueId}</Link></span>,
                  name:  
                        row.householdMemberType === 1 ? 
                      (<>{row.details.firstName + " " + row.details.lastName } <FaRegDotCircle size="10" style={{color:'green'}}/></>) : 
                      
                      (<>{row.details.firstName + " " + row.details.lastName } <FaRegDotCircle size="10" style={{color:'blue'}}/></>)
                      ,
                  date: row.details && row.details.dateOfEnrolment ? row.details.dateOfEnrolment : null,
                  type: "",
                  
                      
                  age: (row.details.dateOfBirthType === 'estimated' ? '~' : '') + calculateAge(row.details.dob),
                  action:
                  <Menu>
                          <MenuButton style={{ backgroundColor:"#3F51B5", color:"#fff", border:"2px solid #3F51B5", borderRadius:"4px"}}>
                              Action <span aria-hidden>▾</span>
                            </MenuButton>
                              <MenuList style={{hover:"#eee"}}>
                              <MenuItem >
                                <Link
                                      to={{pathname: "/household-member/home" , state:row.id, householdId:row.householdId}}>
                                      View Dashboard
                                </Link>
                                
                              </MenuItem>
                              {/*<MenuItem >{" "}Delete</MenuItem>*/}
                              </MenuList>
                          </Menu>
                  
                }))}              
                    
                options={{
                  search: true
                }}
              />
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </>
  )
}

const mapStateToProps = state => {
  return {
      houseMemberList: state.houseHoldMember.members
  };
};
const mapActionToProps = {
  fetchAllMember: fetchAllHouseHoldMember
};

export default connect(mapStateToProps, mapActionToProps)(HouseholdMember);
