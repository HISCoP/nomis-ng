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
import { connect } from "react-redux";
import { fetchAllHouseHoldMember } from "./../../../actions/houseHoldMember";


const HouseholdMember = (props) => {

  const [loading, setLoading] = useState('')

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
  //Function to calculate Members Age 
  function age(dob)
    {
        
        dob = new Date(dob);
        return   new Number((new Date().getTime() - dob.getTime()) / 31536000000).toFixed(0);
    }


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
                  { title: 'OVC ID', field: 'id' },
                  { title: 'Date Assessed', field: 'date' },
                  { title: 'Name', field: 'name' },
                  {
                    title: 'Age',
                    field: 'age',
                    
                  },
                  {
                    title: 'Action',
                    field: 'action',
                    
                  },
                ]}
                isLoading={loading}
                data={props.houseMemberList.map((row) => ({
                  id: row.id,
                  date: null,
                  name: row.firstName + " " + row.lastName,
                  age: age(row.dob),
                  action:
                  <Menu>
                          <MenuButton style={{ backgroundColor:"#3F51B5", color:"#fff", border:"2px solid #3F51B5", borderRadius:"4px"}}>
                              Action <span aria-hidden>▾</span>
                            </MenuButton>
                              <MenuList style={{hover:"#eee"}}>
                              <MenuItem >
                                <Link
                                      to={{pathname: "/household-member/home" , houseHoldId:row.householdId}}>
                                      View Dashboard
                                </Link>
                                
                              </MenuItem>
                              <MenuItem >{" "}Edit</MenuItem>
                              <MenuItem >{" "}Delete</MenuItem>
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
