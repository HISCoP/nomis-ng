import React, {useState} from 'react'
import {
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CButton,
  CRow
} from '@coreui/react'

import MaterialTable from 'material-table';
import {Menu,MenuList,MenuButton,MenuItem,} from "@reach/menu-button";
import "@reach/menu-button/styles.css";
import { Link } from 'react-router-dom';
import NewHouseHoldAssessment from './NewHouseHoldAssessment';

const Tables = () => {
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);


  return (
    <>
      
        <CRow>
        <CCol>
          <CCard>
            <CCardHeader>
              Household Vulnerability Assessment

              <CButton 
                  color="primary" 
                  className="float-right"
                  onClick={toggle}
                >New Household Assessment</CButton>
            </CCardHeader>
            <CCardBody>
            <MaterialTable
                title="Household List"
                columns={[
                  { title: 'Unique ID', field: 'id' },
                  { title: 'Date Assessed', field: 'date' },
                  { title: 'Total OVC', field: 'ovc', type: 'numeric' },
                  {
                    title: 'Status',
                    field: 'staus',
                    
                  },
                  {
                    title: 'Action',
                    field: 'action',
                    
                  },
                ]}
                data={[
                  { id: '7892', date: 'Baran', ovc: 7, status: 'Vulnerable', 
                  action:
                  <Menu>
                          <MenuButton style={{ backgroundColor:"#3F51B5", color:"#fff", border:"2px solid #3F51B5", borderRadius:"4px"}}>
                              Action <span aria-hidden>▾</span>
                            </MenuButton>
                              <MenuList style={{hover:"#eee"}}>
                              <MenuItem >
                                <Link
                                      to={{pathname: "/household/home"}}>
                                      View Dashboard
                                </Link>
                                
                              </MenuItem>                            
                              <MenuItem >{" "}Edit</MenuItem>
                              <MenuItem >{" "}Delete</MenuItem>
                              </MenuList>
                          </Menu>
                  
                  },
                  { id: '4758', date: 'Baran', ovc: 7, status: 'Not Vulnerable', 
                  action:
                  <Menu>
                          <MenuButton style={{ backgroundColor:"#3F51B5", color:"#fff", border:"2px solid #3F51B5", borderRadius:"4px"}}>
                              Action <span aria-hidden>▾</span>
                            </MenuButton>
                              <MenuList style={{hover:"#eee"}}>
                              <MenuItem >
                                <Link
                                      to={{pathname: "/household/home"}}>
                                      View Dashboard
                                </Link>
                                
                              </MenuItem>                            
                              <MenuItem >{" "}Edit</MenuItem>
                              <MenuItem >{" "}Delete</MenuItem>
                              </MenuList>
                          </Menu>
                  
                  },
                ]}        
                options={{
                  search: true
                }}
              />
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
      <NewHouseHoldAssessment  modal={modal} toggle={toggle}/>
    </>
    
  )
  
}

export default Tables
