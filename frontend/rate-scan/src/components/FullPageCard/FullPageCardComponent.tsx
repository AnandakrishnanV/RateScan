import React from 'react'
import './FullPageCardComponent.scss'

interface FullPageCardProps {
  title: string
  children: React.ReactNode
}

const FullPageCardComponent = ({ title, children }: FullPageCardProps) => {
  return (
    <div className="full-page">
      <div className="card-container">
        <div className="card-content">
          <h1 className='card-title'>{title}</h1>
          <div>{children}</div>
        </div>
      </div>
    </div>
  )
}

export default FullPageCardComponent
