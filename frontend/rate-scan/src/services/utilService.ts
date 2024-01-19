import { formatDistance } from 'date-fns';

export const timeAgo = (dateString: string | number | Date) => {
    return formatDistance(
        new Date(dateString),
        new Date(),
        { addSuffix: true}
    )
}