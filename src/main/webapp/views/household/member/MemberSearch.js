import React from 'react'
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

const Tables = () => {
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
                  { title: 'Name', field: 'name' },
                  { title: 'Address', field: 'surname' },
                  { title: 'Date', field: 'birthYear', type: 'numeric' },
                  {
                    title: 'State',
                    field: 'birthCity',
                    
                  },
                  {
                    title: 'Action',
                    field: 'action',
                    
                  },
                ]}
                data={[
                  { name: 'Mehmet', surname: 'Baran', birthYear: 1987, birthCity: 63, 
                  action:
                  <Menu>
                          <MenuButton style={{ backgroundColor:"#3F51B5", color:"#fff", border:"2px solid #3F51B5", borderRadius:"4px"}}>
                              Action <span aria-hidden>▾</span>
                            </MenuButton>
                              <MenuList style={{hover:"#eee"}}>
                              <MenuItem >
                                <Link
                                      to={{pathname: "/household-member/home"}}>
                                      View Dashboard
                                </Link>
                                
                              </MenuItem>

                              </MenuList>
                          </Menu>
                  
                  },
                  { name: 'Zerya Betül', surname: 'Baran', birthYear: 2017, birthCity: 34, 
                  action:<Menu>
                          <MenuButton style={{ backgroundColor:"#3F51B5", color:"#fff", border:"2px solid #3F51B5", borderRadius:"4px"}}>
                              Action <span aria-hidden>▾</span>
                            </MenuButton>
                              <MenuList style={{hover:"#eee"}}>
                              <MenuItem >{" "}View</MenuItem>
                              <MenuItem >{" "}Second View</MenuItem>
                              <MenuItem >{" "}Third View</MenuItem>
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
    </>
  )
}

export default Tables
