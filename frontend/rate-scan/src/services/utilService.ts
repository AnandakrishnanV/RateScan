import { formatDistance } from 'date-fns';

export const timeAgo = (dateString: string | number | Date) => {

    const date = new Date(dateString)
    const now = new Date()
    const secondsDiff = (now.getTime() - date.getTime()) / 1000

    if(secondsDiff < 60) {
        return `${Math.round(secondsDiff)} seconds ago`
    }

    return formatDistance(
        date,
        now,
        { addSuffix: true}
    )
}