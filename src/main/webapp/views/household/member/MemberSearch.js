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

import { forwardRef } from 'react';
import axios from "axios";
import {url as baseUrl} from "./../../../api";
import AddBox from '@material-ui/icons/AddBox';
import ArrowUpward from '@material-ui/icons/ArrowUpward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';

const tableIcons = {
Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />),
ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
};


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
                icons={tableIcons}
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
               
                data={query =>
                  new Promise((resolve, reject) =>
                      axios.get(`${baseUrl}household-members/?size=${query.pageSize}&page=${query.page}&search=${query.search}`)
                          .then(response => response)
                          .then(result => {
                            
                              resolve({
                                  data: result.data.map((row) => ({
                                    id: <span> <Link
                            to={{pathname: "/household-member/home", state: row.id, householdId:row.householdId }}>{row.details.uniqueId}</Link></span>,
                            name:  
                                  row.householdMemberType === 1 ? 
                                (<><FaRegDotCircle size="10" style={{color:'green'}}/> {row.details.firstName + " " + row.details.lastName } </>) : 
                                
                                (<><FaRegDotCircle size="10" style={{color:'blue'}}/> {row.details.firstName + " " + row.details.lastName } </>)
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
                                     
                                  })),
                                  page: query.page,
                                  totalCount: result.headers['x-total-count'],
                              })
                          })
                  )}      
                  options={{
                   
                    searchFieldStyle: {
                        width : '200%',
                        margingLeft: '250px',
                    },
                    filtering: false,
                    exportButton: false,
                    searchFieldAlignment: 'left',
                    pageSizeOptions:[10,20,100],
                    pageSize:10,
                    debounceInterval: 400
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
