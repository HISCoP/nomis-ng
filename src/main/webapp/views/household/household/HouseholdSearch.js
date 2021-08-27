import React, {useState, useEffect} from 'react'
import {
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CButton,
  CRow
} from '@coreui/react'
import { connect } from "react-redux";
import MaterialTable from 'material-table';
import {Menu,MenuList,MenuButton,MenuItem,} from "@reach/menu-button";
import "@reach/menu-button/styles.css";
import { Link } from 'react-router-dom';
import NewHouseHoldAssessment from './NewHouseHoldAssessment';
import NewHouseHold from './NewHouseHold';
import { fetchAllHouseHold } from "./../../../actions/houseHold";


const HouseHoldList = (props) => {
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    performSearch();
  }, []); //componentDidMount

    const performSearch = () => {
        setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)
        }
        props.fetchAllHouseHold(onSuccess, onError);
    }
  return (
    <>
      
        <CRow>
        <CCol>
          <CCard>
            <CCardHeader>
             Find Household

              <CButton 
                  color="primary" 
                  className="float-right"
                  onClick={toggle}
                >New Household </CButton>
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
                    field: 'status',
                    
                  },
                  {
                    title: 'Action',
                    field: 'action',
                    
                  },
                ]}
                data={props.houseHoldList.map((row) => ({
                  id: <span> <Link
                      to={{pathname: "/household/home", state: row.id }}>{row.uniqueId}</Link></span>,
                  date: row.details && row.details.assessmentDate ? row.details.assessmentDate : "",
                  ovc: row.details &&  row.details.noOfChildren != null ?  row.details.noOfChildren : 0,
                  status: row.status,
                  action:
                          <Menu>
                            <MenuButton style={{ backgroundColor:"#3F51B5", color:"#fff", border:"2px solid #3F51B5", borderRadius:"4px"}}>
                                Action <span aria-hidden>▾</span>
                              </MenuButton>
                                <MenuList style={{hover:"#eee"}}>
                                <MenuItem >
                                  <Link
                                        to={{pathname: "/household/home", state: row.id }}>
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
      {modal ?
        <NewHouseHold  modal={modal} toggle={toggle} reloadSearch={performSearch}/>
        :
        ""
      }
      
    </>
    
  )
  
}


const mapStateToProps = state => {
  return {
      houseHoldList: state.houseHold.houseHoldList
  };
};
const mapActionToProps = {
  fetchAllHouseHold: fetchAllHouseHold
};

export default connect(mapStateToProps, mapActionToProps)(HouseHoldList);

