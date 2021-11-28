import React, { Suspense } from 'react'
import {
  Redirect,
  Route,
  Switch
} from 'react-router-dom'
import { CContainer, CFade } from '@coreui/react'
import { Spinner } from 'reactstrap';

// routes config
import routes from '../routes'
  
const loading = (
  <div className="pt-10 text-center">
    <div className="sk-spinner sk-spinner-pulse">
    <Spinner style={{ width: '3rem', height: '3rem' }} />
    </div>
  </div>
)

const TheContent = () => {
  return (
    <main className="c-main">
      <CContainer fluid>
        <Suspense fallback={loading}>
        <Redirect from="/" to="/login" />
          <Switch>
          <Redirect from="/" to="/login" />
            {routes.map((route, idx) => {
              return route.component && (
                <Route
                  key={idx}
                  path={route.path}
                  exact={route.exact}
                  name={route.name}
                  render={props => (
                    <CFade>
                      <route.component {...props} />
                    </CFade>
                  )} />
              )
            })}
            
          </Switch>
        </Suspense>
      </CContainer>
    </main>
  )
}

export default React.memo(TheContent)
