import { formatDistance } from 'date-fns'

export const timeAgo = (dateString: string | number | Date) => {
  const date = new Date(dateString)
  const now = new Date()
  const secondsDiff = (now.getTime() - date.getTime()) / 1000

  if (secondsDiff < 60) {
    return `${Math.round(secondsDiff)} seconds ago`
  }

  return formatDistance(date, now, { addSuffix: true })
}

export const timeLeft = (dateString: string | number | Date) => {
  const date = new Date(dateString)
  const now = new Date()

  return formatDistance(date, now, { addSuffix: true })
}

export const timeLeftDays = (dateString: string | number | Date) => {
  const date = new Date(dateString)
  const now = new Date()
  const diff = (date.getTime() - now.getTime()) / (1000 * 60 * 60)

  console.log(diff);

  if (diff < 1) {
    return `In Minutes`
  } else if (checkIfNextDay(date)) {
    return `In 1 Day`
  } else if (diff < 12) {
    return `In ${Math.round(diff)} Hours`
  } else if (diff < 24) {
    return `In 1 Day`
  } else {
    return formatDistance(date, now, { addSuffix: true })
  }
}

const checkIfNextDay = (date: Date) => {
  const tomorrow = new Date(
    date.getFullYear(),
    date.getMonth(),
    date.getDate() + 1,
  )
  if (
    tomorrow.getFullYear() == date.getFullYear() &&
    tomorrow.getMonth() == date.getMonth() &&
    tomorrow.getDate() == date.getDate()
  ) {
    return true
  }
  return false
}
